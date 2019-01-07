from post import models
from rest_framework import serializers
from utils import *
from datetime import *
from login import models as lm
import pprint

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


class MentionedTimeSerialzer(serializers.ModelSerializer):
    class Meta:
        model = models.PointMentionedTime
        fields = "__all__"


class OwnerSerializer(serializers.ModelSerializer):
    photo = serializers.SerializerMethodField()

    class Meta:
        model = lm.RegisteredUser
        fields = [
            "id",
            "username",
            "photo"
        ]

    def get_photo(self, obj):
        photo = lm.ProfilePhoto.objects.filter(user=obj.id)
        if photo.exists():
            return photo.first().image.__str__()
        return None


class MemoryCommentSerializer(serializers.ModelSerializer):
    # owner = serializers.SerializerMethodField()
    comment_time = serializers.SerializerMethodField()

    class Meta:
        model = models.MemoryComment
        fields = [
            "id",
            "comment",
            "owner",
            "comment_time"
        ]

    # def get_owner(self, obj):
    #     owner = lm.RegisteredUser.objects.get(id=obj.owner)
    #     return OwnerSerializer(owner).data

    def get_comment_time(self, obj):
        return dateFormat_hour(obj.comment_time)


class MemoryCommentListSerializer(serializers.ModelSerializer):
    owner = serializers.SerializerMethodField()
    comment_time = serializers.SerializerMethodField()

    class Meta:
        model = models.MemoryComment
        fields = [
            "id",
            "comment",
            "owner",
            "comment_time"
        ]

    def get_owner(self, obj):
        owner = lm.RegisteredUser.objects.get(id=obj.owner_id)
        return OwnerSerializer(owner).data

    def get_comment_time(self, obj):
        return dateFormat_hour(obj.comment_time)


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
            "location",
            "mentioned_time",
            "liked_users"
        ]

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
    numcomments = serializers.SerializerMethodField()
    comments = serializers.SerializerMethodField()
    mentioned_time = serializers.SerializerMethodField()
    location = serializers.SerializerMethodField()

    # liked_users = serializers.SerializerMethodField()

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
            "numcomments",
            "location",
            "mentioned_time",
            "liked_users"
        ]

    # def get_liked_users(self, obj):
    #     users = obj.liked_users.all()
    #     return OwnerSerializer(users, many=True).data

    def get_mentioned_time(self, obj):
        mentioned_time = models.PointMentionedTime.objects.filter(id=obj.mentioned_time_id)
        return MentionedTimeSerialzer(mentioned_time, many=True).data

    def get_numcomments(self, obj):
        return obj.comments.all().count()

    def get_comments(self, obj):
        comments = obj.comments.all().order_by('-comment_time')
        return MemoryCommentListSerializer(comments, many=True).data

    def get_owner(self, obj):
        owner = lm.RegisteredUser.objects.get(id=obj.owner_id)
        return OwnerSerializer(owner).data

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
    
    def get_location(self,obj):
        location = models.Location.objects.filter(id=obj.location_id)
        return LocationSerializer(location, many=True).data


class MemoryMultimediaSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.MemoryMultimediaUpload
        fields = "__all__"


class MemoryCreate1Serializer(serializers.ModelSerializer):
    class Meta:
        model = models.Memory
        fields = "__all__"


class PointLocationSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.PointLocation
        fields = "__all__"

class LocationSerializer(serializers.ModelSerializer):
    location_list = serializers.SerializerMethodField()
    class Meta:
        model = models.Location
        fields = [
            "location_type",
            "location_list"
        ]
    def get_location_list(self, obj):
        location = obj.location_list.all()
        return PointLocationSerializer(location, many=True).data

