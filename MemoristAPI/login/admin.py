from django.contrib import admin
from login import models as lm

# Register your models here.
admin.site.register(lm.RegisteredUser)
admin.site.register(lm.Activation)
