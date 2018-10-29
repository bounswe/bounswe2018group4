from django.shortcuts import render
import json
# Create your views here.
from rest_framework.generics import CreateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from post import serializers as postserializers
from post import models as postmodels


class MemoryCreateAPIView(CreateAPIView):
    serializer_class = postserializers.MemorySerializer
    permission_classes = IsAuthenticated,

    def post(self, *args, **kwargs):
        user = self.request.user
        data = self.request.data
        print(data)
        files = self.request.FILES.getlist("multimedia")
        data.update({"owner": user.id})
        sr = postserializers.MemorySerializer(data=data)
        sr.is_valid(raise_exception=True)
        sr.save()
        memory = postmodels.Memory.objects.filter(id=sr.data["id"]).first()
        multi = 0
        text = 0
        ord = 0
        story = json.loads(data["story"])["story"]
        for f in json.loads(data["format"])["format"]:
            print(f)
            if f is "T":
                newstory = postmodels.MemoryItemText(
                    memory=memory,
                    order=ord,
                    text=story[text]
                )
                newstory.save()
                text += 1
                print("a")

            elif f is "M":
                newmultimedia = postmodels.MemoryItemMultimedia(
                    memory=memory,
                    order=ord,
                    multimedia=files[multi]
                )
                newmultimedia.save()
                multi += 1
                print("m")
            ord += 1

        return Response({"status": "ok"}, status=200)
