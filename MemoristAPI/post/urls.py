from django.conf.urls import url
from post.views import *

urlpatterns = [
    url('^create/$', MemoryCreateAPIView.as_view()),
    url('^create1/$', MemoryCreate1APIView.as_view()),
    url('^list/$', MemoryListAPIView.as_view()),
    url('^get_memory/(?P<pk>\d+)/$', GetMemoryAPIView.as_view()),
    url('^delete/(?P<pk>\d+)/$', MemoryDeleteAPIView.as_view()),
    url('^like_post/(?P<pk>\d+)/$', MemoryLikeAPIView.as_view()),
    url('^create_comment/(?P<pk>\d+)/$', MemoryCommentCreateAPIView.as_view()),
    url('^media/(?P<pk>\d+)/$', UploadMemoryMultimediaAPIView.as_view()),
    url('^delete_comment/$', CommentDeleteAPIView.as_view()),
    url('^dislike/(?P<pk>\d+)/$', MemoryDislikeAPIView.as_view()),
    url('^homepage/$', HomepageAPIView.as_view()),
    url('^memory_search/(?P<pk>[a-zA-Z0-9_]+)/$', SearchMemoryAPIView.as_view()),
    url('^memory_recommendations/$', MemoryRecommendationsAPIView.as_view()),
    url('^top_memories/$', TopMemoriesAPIView.as_view())
]
