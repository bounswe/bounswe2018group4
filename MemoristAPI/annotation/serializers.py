from post import models
from rest_framework import serializers
from utils import *
from datetime import *
from login.models import *
from annotation.models import *


class CreatorSerializer(serializers.ModelSerializer):
    c_id = serializers.SerializerMethodField()

    class Meta:
        model = Creator
        fields = [
            "c_id",
            "c_type",
            "name",
            "nickname"
        ]

    def get_c_id(self, obj):
        print(obj.c_id_id)
        return f"http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/login/get_profile/{obj.c_id_id}/"


class SelectorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Selector
        fields = [
            "s_type",
            "start",
            "end"
        ]


class BodySerializer(serializers.ModelSerializer):
    class Meta:
        model = Body
        fields = [
            "b_type",
            "value"
        ]


class TargetSerializer(serializers.ModelSerializer):
    selector = serializers.SerializerMethodField()
    source = serializers.SerializerMethodField()

    class Meta:
        model = Target
        fields = [
            "t_type",
            "source",
            "selector"
        ]

    def get_selector(self, obj):
        selector = Selector.objects.filter(id=obj.selector_id)
        return SelectorSerializer(selector, many=True).data

    def get_source(self, obj):
        return f"http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/post/get_memory/{obj.source}/"


class AnnotationSerializer(serializers.ModelSerializer):
    creator = serializers.SerializerMethodField()
    body = serializers.SerializerMethodField()
    target = serializers.SerializerMethodField()
    id = serializers.SerializerMethodField()
    created = serializers.SerializerMethodField()

    class Meta:
        model = Annotation
        fields = [
            "context",
            "id",
            "a_type",
            "motivation",
            "creator",
            "created",
            "body",
            "target"
        ]

    def get_id(self, obj):
        return f"http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/annotation/get/{obj.id}/"

    def get_creator(self, obj):
        creator = Creator.objects.filter(id=obj.creator_id)
        return CreatorSerializer(creator, many=True).data

    def get_body(self, obj):
        body = Body.objects.filter(id=obj.body_id)
        return BodySerializer(body, many=True).data

    def get_target(self, obj):
        target = Target.objects.filter(id=obj.target_id)
        return TargetSerializer(target, many=True).data

    def get_created(self, obj):
        return dateFormat_hour(obj.created)
