from django.conf.urls import url
from annotation.views import *

urlpatterns = [
    url(r"^create/$", CreateAnnotationAPIView.as_view()),
    url(r"^get/(?P<pk>\d+)/$", GetAnnotationAPIView.as_view()),
    url(r"^get_annotations/(?P<pk>\d+)/$", GetAnnotationsAPIView.as_view())
]
