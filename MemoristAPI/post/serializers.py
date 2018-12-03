from post import models
from rest_framework import serializers
from utils import *
from datetime import *
from login import models as lm


class MemoryTagSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryTag
        fields = "__all__"


class MemoryItemTextSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryItemText
        fields = "__all__"


class MemoryMultimediaUploadSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryMultimediaUpload
        fields = [
            "media"
        ]


class MemoryItemMultimediaSerializer(serializers.ModelSerializer):
    multimedia = serializers.SerializerMethodField()

    class Meta:
        model = models.MemoryItemMultimedia
        fields = [
            "id",
            "media_type",
            "order",
            "memory",
            "multimedia"
        ]

    def get_multimedia(self, obj):
        multimedia = models.MemoryMultimediaUpload.objects.filter(id=obj.multimedia.id).first()
        return MemoryMultimediaUploadSerializer(multimedia).data


class MemorySerializer(serializers.ModelSerializer):
    tags = serializers.SerializerMethodField()
    owner = serializers.SerializerMethodField()
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

    def get_owner(self, obj):
        owner = lm.RegisteredUser.objects.get(id=obj.owner_id)
        owner = owner.username
        return owner

    def get_posting_time(self, obj):
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


class Memory1Serializer(serializers.ModelSerializer):
    tags = serializers.SerializerMethodField()
    owner = serializers.SerializerMethodField()
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

    def get_owner(self, obj):
        owner = lm.RegisteredUser.objects.get(id=obj.owner_id)
        owner = owner.username
        return owner

    def get_posting_time(self, obj):
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


class MemoryMultimediaSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryMultimediaUpload
        fields = "__all__"


class MemoryCreate1Serializer(serializers.ModelSerializer):
    class Meta:
        model = models.Memory
        fields = "__all__"
