from django.conf.urls import url
from post.views import MemoryCreateAPIView

urlpatterns = [
    url('^create/$', MemoryCreateAPIView.as_view()),

]
