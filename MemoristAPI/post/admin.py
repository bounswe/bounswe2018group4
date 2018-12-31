from django.contrib import admin
from post import models

# Register your models here.
admin.site.register(models.Memory)
admin.site.register(models.MemoryItemMultimedia)
admin.site.register(models.MemoryItemText)
admin.site.register(models.MemoryTag)
admin.site.register(models.PointLocation)
admin.site.register(models.PointMentionedTime)
admin.site.register(models.Location)
admin.site.register(models.MemoryComment)
admin.site.register(models.MemoryMultimediaUpload)
