from rest_framework import serializers
from ..models import Oferta


class OfertaSerializer(serializers.ModelSerializer):
    fechaVencimiento = serializers.DateTimeField(required=True, source="fecha_vencimiento")

    class Meta:
        model = Oferta
        fields = ['id', 'nombre', 'descuento', 'fechaVencimiento']
