from django.urls import path,include
from . import views
from rest_framework.routers import DefaultRouter


router=DefaultRouter()

router.register("insta-vendor-info",views.VendorInstaViewSet, basename='insta-vendor-info')
router.register("vendor-info",views.VendorViewSet, basename='vendor-info')
router.register("all-vendor-info",views.AllVendorViewSet, basename='all-vendor-info')


urlpatterns = [
    path("scrape-vendors/",views.VendorViewSet.as_view({"get":"scrape_vendors"}),name="scrape-vendors"),
    path("get-vendors-by-category/<str:category>/",views.AllVendorViewSet.as_view({"get":"get_vendors_by_category"}),name="get-vendors-by-category"),
    path("scrape-insta-vendors/",views.VendorInstaViewSet.as_view({"get":"scrape_insta_vendors","post":"scrape_insta_vendors"}),name="scrape-insta-vendors")


]+router.urls