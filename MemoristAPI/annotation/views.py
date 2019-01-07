from django.shortcuts import render
import json

from rest_framework.status import *
from rest_framework.generics import *
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView
from annotation.serializers import *


class CreateAnnotationAPIView(APIView):
    permission_classes = IsAuthenticated,

    def post(self, *args, **kwargs):
        data = self.request.data
        user = self.request.user
        user = RegisteredUser.objects.get(id=user.id)
        creator = Creator(
            c_id=user,
            c_type=data["creator"]["type"],
            name=user.get_full_name(),
            nickname=user.username
        )
        creator.save()
        body = Body(
            b_type=data["body"]["type"],
            value=data["body"]["value"]
        )
        body.save()
        selector = Selector(
            s_type=data["target"]["selector"]["type"],
            start=data["target"]["selector"]["start"],
            end=data["target"]["selector"]["end"],

        )
        selector.save()
        target = Target(
            t_type=data["target"]["type"],
            source=data["target"]["source"],
            selector=selector
        )
        target.save()
        annotation = Annotation(
            context=data["context"],
            a_type=data["type"],
            motivation=data["motivation"],
            creator=creator,
            body=body,
            target=target
        )
        annotation.save()

        ann = AnnotationSerializer(annotation)
        return Response(ann.data, status=HTTP_200_OK)


class GetAnnotationAPIView(RetrieveAPIView):
    permission_classes = IsAuthenticated,
    serializer_class = AnnotationSerializer

    def get_queryset(self):
        an = self.kwargs["pk"]
        return Annotation.objects.filter(id=an)


class GetAnnotationsAPIView(ListAPIView):
    permission_classes = IsAuthenticated,
    serializer_class = AnnotationSerializer

    def get_queryset(self):
        an = self.kwargs["pk"]
        return Annotation.objects.filter(target__source=an)
