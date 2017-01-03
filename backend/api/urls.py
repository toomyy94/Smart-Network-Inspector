from django.conf.urls import include, url
from rest_framework.generics import ListAPIView
from views import InfoViewer, InfoAdd
from core.models import InfoStorage
from serializers import InfoStorageSerializer


urlpatterns = [
    # ex: /api/backend/info/1/
    url(r'^backend/info/(?P<pk>[0-9]+)/$', InfoViewer.as_view()),
    # ex: /api/backend/info/
    url(r'^backend/info/$', InfoAdd.as_view()),
    # ex: /api/backend/info/all/
    # url(r'^backend/info/all/$', InfoViewerAll.as_view()),
    url(r'^backend/info/all/$', ListAPIView.as_view(queryset=InfoStorage.objects.all(),
                                                    serializer_class=InfoStorageSerializer)),
]
