from django.contrib import admin
from django.conf.urls import url, include
from django.conf import settings
from django.conf.urls.static import static
 
urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^Student_Data/', include('Student_Data.urls')),
    url(r'^Prof_Data/', include('Prof_Data.urls')),
    url(r'api-auth/', include('rest_framework.urls')),
] + static(settings.MEDIA_URL, document_root = settings.MEDIA_ROOT)