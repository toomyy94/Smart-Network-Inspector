from rest_framework import serializers
from core.models import InfoStorage


class InfoStorageSerializer(serializers.ModelSerializer):
    class Meta:
        model = InfoStorage
        fields = '__all__'
