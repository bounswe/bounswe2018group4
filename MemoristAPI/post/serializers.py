from post import models
from rest_framework import serializers
from utils import *
from datetime import *


class MemoryTagSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryTag
        fields = "__all__"


class MemoryItemTextSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryItemText
        fields = "__all__"


class MemoryItemMultimediaSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryItemMultimedia
        fields = "__all__"


class MemorySerializer(serializers.ModelSerializer):
    tags = serializers.SerializerMethodField()
    texts = serializers.SerializerMethodField()
    multimedia = serializers.SerializerMethodField()
    posting_time = serializers.SerializerMethodField()

    class Meta:
        model = models.Memory
        fields = [
            "id",
            "owner",
            "posting_time",
            "title",
            "texts",
            "multimedia",
            "tags",
            "numlikes",
            "comments",
            "pointlocations",
            "pathlocations",
            "mentioned_time",
            "mentioned_time_period",
            "liked_users"
        ]

    def get_posting_time(self, obj):
        print(obj.posting_time)
        return dateFormat_hour(obj.posting_time)

    def get_texts(self, obj):
        texts = models.MemoryItemText.objects.filter(memory=obj.id)
        return MemoryItemTextSerializer(texts, many=True).data

    def get_multimedia(self, obj):
        multimedia = models.MemoryItemMultimedia.objects.filter(memory=obj.id)
        return MemoryItemMultimediaSerializer(multimedia, many=True).data

    def get_tags(self, obj):
        tags = models.MemoryTag.objects.filter(memory=obj.id)
        return MemoryTagSerializer(tags, many=True).data


class MemoryCommentSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryComment
        fields = "__all__"
