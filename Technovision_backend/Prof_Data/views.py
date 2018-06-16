from django.shortcuts import render
from rest_framework import viewsets, permissions
from .models import Prof
from .serializers import ProfSerializer

class ProfView(viewsets.ModelViewSet):
    queryset = Prof.objects.all()
    
    serializer_class = ProfSerializer
