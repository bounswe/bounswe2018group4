from django.shortcuts import render
import json

from rest_framework.status import *
from rest_framework.generics import *
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView

from post import serializers as postserializers
from post import models as postmodels
from login.models import *


class MemoryCreateAPIView(CreateAPIView):
    serializer_class = postserializers.MemorySerializer
    permission_classes = IsAuthenticated,

    def post(self, *args, **kwargs):
        user = self.request.user
        data = self.request.data.copy()
        print(data)
        print(type(data))
        files = self.request.FILES.getlist("multimedia")
        data.update({"owner": user.id})
        sr = postserializers.MemorySerializer(data=data)
        sr.is_valid(raise_exception=True)
        sr.save()
        memory = postmodels.Memory.objects.filter(id=sr.data["id"]).first()
        multi = 0
        text = 0
        ord = 0
        print(json.loads(data["story"]))
        print(json.loads(data["format"]))
        story = json.loads(data["story"])["story"]
        for f in json.loads(data["format"])["format"]:
            if f is "T":
                newstory = postmodels.MemoryItemText(
                    memory=memory,
                    order=ord,
                    text=story[text]
                )
                newstory.save()
                text += 1

            elif f in ["I", "V", "A"]:
                newmultimedia = postmodels.MemoryItemMultimedia(
                    memory=memory,
                    order=ord,
                    media_type=1 if (f is "I") else 2 if (f is "V") else 3,
                    multimedia=files[multi]
                )
                newmultimedia.save()
                multi += 1
            ord += 1
        tags = json.loads(data["tags"])["tags"]
        for t in tags:
            tag = postmodels.MemoryTag(
                memory=memory,
                tag=t
            )
            tag.save()
        serializer = postserializers.MemorySerializer(memory)
        return Response(serializer.data, status=HTTP_200_OK)


class MemoryListAPIView(ListAPIView):
    permission_classes = IsAuthenticated,
    serializer_class = postserializers.Memory1Serializer

    def get_queryset(self):
        return postmodels.Memory.objects.filter(owner=self.request.user)


class MemoryLikeAPIView(APIView):
    permission_classes = IsAuthenticated,

    def get(self, *args, **kwargs):
        m_id = self.kwargs["pk"]
        user = RegisteredUser.objects.filter(id=self.request.user.id)
        memory = postmodels.Memory.objects.filter(id=m_id)
        if user.exists():
            user = user.first()
            if not memory.exists():
                return Response({"detail": "memory does not exists"}, status=HTTP_200_OK)
            elif memory.exists():
                if not memory.filter(liked_users=user).exists():
                    memory = memory.first()
                    memory.numlikes += 1
                    memory.liked_users.add(user)
                    memory.save()
                    return Response({"like": memory.numlikes}, status=HTTP_200_OK)
                return Response({"like": memory.first().numlikes}, status=HTTP_200_OK)
        return Response({"detail": "user does not exists"}, status=HTTP_400_BAD_REQUEST)


class MemoryCommentCreateAPIView(CreateAPIView):
    permission_classes = IsAuthenticated,

    def post(self, *args, **kwargs):
        user = RegisteredUser.objects.get(id=self.request.user.id)
        data = self.request.data
        dt = dict()
        dt["owner"] = user.id
        dt["comment"] = data["comment"]
        serializer = postserializers.MemoryCommentSerializer(data=dt)
        serializer.is_valid(raise_exception=True)
        m_id = self.kwargs['pk']
        memory = postmodels.Memory.objects.filter(id=m_id)
        if not memory.exists():
            return Response({"detail": "memory does not exists"}, status=HTTP_400_BAD_REQUEST)
        memory = memory.first()
        serializer.save()
        comment = postmodels.MemoryComment.objects.get(id=serializer.data["id"])
        memory.comments.add(comment)
        memory.save()
        return Response(memory.comments.all().values(), status=HTTP_200_OK)


class UploadMemoryMultimediaAPIView(CreateAPIView):
    serializer_class = postmodels.MemoryMultimediaUpload

    def post(self, request, *args, **kwargs):
        media = postmodels.MemoryMultimediaUpload(
            media=self.request.FILES.get("media"),
            media_type=self.kwargs["pk"] if (int(self.kwargs["pk"]) < 4) else 3
        )
        media.save()
        serializer = postserializers.MemoryMultimediaSerializer(media)
        return Response(serializer.data, status=HTTP_200_OK)


class MemoryCreate1APIView(CreateAPIView):
    serializer_class = postserializers.MemorySerializer
    permission_classes = IsAuthenticated,

    def post(self, request, *args, **kwargs):
        user = self.request.user
        data = self.request.data.copy()
        data.update({"owner": user.id})
        sr = postserializers.MemorySerializer(data=data)
        sr.is_valid(raise_exception=True)
        sr.save()
        memory = postmodels.Memory.objects.filter(id=sr.data["id"]).first()
        multi = 0
        text = 0
        ord = 0
        story = data["story"]
        media = data["media"]
        for f in data["format"]:
            if f is "T":
                newstory = postmodels.MemoryItemText(
                    memory=memory,
                    order=ord,
                    text=story[text]
                )
                newstory.save()
                text += 1

            elif f in ["I", "V", "A"]:
                newmultimedia = postmodels.MemoryItemMultimedia(
                    memory=memory,
                    order=ord,
                    media_type=1 if (f is "I") else 2 if (f is "V") else 3,
                    multimedia=postmodels.MemoryMultimediaUpload.objects.get(id=media[multi])
                )
                newmultimedia.save()
                multi += 1
            ord += 1
        tags = data["tags"]
        for t in tags:
            tag = postmodels.MemoryTag(
                memory=memory,
                tag=t
            )
            tag.save()
        serializer = postserializers.Memory1Serializer(memory)
        return Response(serializer.data, status=HTTP_200_OK)
