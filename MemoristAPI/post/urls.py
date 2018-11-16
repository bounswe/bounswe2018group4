from django.conf.urls import url
from post.views import *

urlpatterns = [
    url('^create/$', MemoryCreateAPIView.as_view()),
    url('^list/$', MemoryListAPIView.as_view()),
    url('^delete/(?P<pk>\d+)/$', MemoryListAPIView.as_view()),
    url('^like_post/(?P<pk>\d+)/$', MemoryLikeAPIView.as_view()),
    url('^create_comment/(?P<pk>\d+)/$', MemoryCommentCreateAPIView.as_view()),

]
