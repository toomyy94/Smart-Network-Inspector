from rest_framework.permissions import IsAuthenticated
from rest_framework.authentication import SessionAuthentication, BasicAuthentication
from rest_framework.parsers import JSONParser

from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status

from core.models import InfoStorage
# from serializers import InfoStorageSerializer
# from rest_framework import generics
# from django.core import serializers
# from django.forms.models import model_to_dict

# import json


class InfoViewer(APIView):
    # permission_classes = (IsAuthenticated,)
    allowed_methods = ['GET', 'DELETE']

    def get(self, request, pk=None):
        """
        Get info by given ID

        <h3>Details</h3>

        <b>METHODS:</b>
            - GET

        <b>RETURNS:</b>
            - 200 OK
            - 404 NOT FOUND
            - 400 BAD REQUEST
        """
        try:
            if pk is not None:
                int_id = int(pk)
                try:
                    info = InfoStorage.objects.get(pk=int_id)
                except:
                    return Response(status=status.HTTP_404_NOT_FOUND, data={'detail': 'Info not found.'})
                payload = {
                    'id': info.pk,
                    'lat': info.lat,
                    'lon': info.lon,
                    'info': info.info,
                    'timestamp': info.msg_time,
                }
                return Response(status=status.HTTP_200_OK, data=payload)
        except:
            pass
        return Response(status=status.HTTP_400_BAD_REQUEST, data={'detail': 'Bad request.'})

    def delete(self, request, pk=None):
        try:
            if pk is not None:
                int_id = int(pk)
                try:
                    info = InfoStorage.objects.get(pk=int_id)
                    info.delete()
                except:
                    return Response(status=status.HTTP_404_NOT_FOUND, data={'detail': 'Info not found.'})
                return Response(status=status.HTTP_200_OK, data={'detail': 'Deleted with success.'})
        except:
            pass
        return Response(status=status.HTTP_400_BAD_REQUEST, data={'detail': 'Bad request.'})


# class InfoViewerAll(APIView):
#     # permission_classes = (IsAuthenticated,)
#     queryset = InfoStorage.objects.all()
#     serializer_class = InfoStorageSerializer
#     allowed_methods = ['GET']
#
#     def get(self, request):
#         """
#         Get all storage data
#
#         <h3>Details</h3>
#
#         <b>METHODS:</b>
#             - GET
#
#         <b>RETURNS:</b>
#             - 200 OK
#             - 404 NOT FOUND
#             - 400 BAD REQUEST
#         """
#         try:
#             res = {}
#             info = InfoStorage.objects.all()
#             print self.serializer_class
#
#             # info = serializers.serialize('json', InfoStorage.objects.all())
#             # data = json.dumps(info)
#             # dict_obj = model_to_dict(self.queryset)
#             # serialized = json.dumps(dict_obj)
#             # result = [storage for storage in InfoStorage.objects.all()]
#             return Response(status=status.HTTP_200_OK, data=res)
#         except:
#             pass
#         return Response(status=status.HTTP_400_BAD_REQUEST, data={'detail': 'Bad request.'})


class InfoAdd(APIView):
    # permission_classes = (IsAuthenticated,)
    parser_classes = (JSONParser,)
    allowed_methods = ['POST']

    def post(self, request):
        """
        Add new info in the database

        <h3>Details</h3> Example:

            {

                "lat": 40.6327429,
                "lon": -8.6580298,
                "info": "This is a test"

            }

        <b>METHODS:</b>
            - POST

        <b>RETURNS:</b>
            - 200 OK
            - 404 NOT FOUND
            - 400 BAD REQUEST
        """
        # X-CSRFToken: CXhYB2UugjkpW8bQsEO269dxeCoigj8T
        # Authorization: Token bc5c07b1993cf54d05d965c1ffc8e6b024976888
        # Content-Type: application/json
        # print(request.META['CSRF_COOKIE'])
        try:
            if 'lat' in request.data and 'lon' in request.data and 'info' in request.data:
                lat = request.data['lat']
                lon = request.data['lon']
                info = request.data['info']
                try:
                    new_info = InfoStorage(lat=lat, lon=lon, info=info)
                    new_info.save()
                    return Response(status=status.HTTP_200_OK, data={'detail': 'Added with success.'})
                except:
                    return Response(status=status.HTTP_404_NOT_FOUND, data={'detail': 'Error adding new info.'})
        except:
            pass
        return Response(status=status.HTTP_400_BAD_REQUEST, data={'detail': 'Bad request.'})
