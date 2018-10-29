from post import models
from rest_framework import serializers

class MemorySerializer(serializers.ModelSerializer):
    class Meta:
        model = models.Memory
        fields = "__all__"



