from django.contrib import admin
from annotation.models import *
# Register your models here.
admin.site.register(Annotation)
admin.site.register(Body)
admin.site.register(Target)
admin.site.register(Creator)
admin.site.register(Selector)