
from rest_framework import serializers
from . import models


class VendorInstaSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.InstaVendorInfo
        exclude=["vendor_price","id"]


class VendorSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.VendorInfo
        exclude=["vendor_following_num","vendor_followers_num","id"]


class AllVendorSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.AllVendorInfo
        fields="__all__"



class ScrapeInstaVendorSerializer(serializers.Serializer):
    links_list = serializers.ListField(
        child=serializers.URLField(),
        required=True
    )
    category_list = serializers.ListField(
        child=serializers.CharField(),
        required=False
    )