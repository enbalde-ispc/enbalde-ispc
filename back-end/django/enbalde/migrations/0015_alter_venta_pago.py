# Generated by Django 5.0.4 on 2024-05-12 21:08

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('enbalde', '0014_venta_pago_venta_transaccion'),
    ]

    operations = [
        migrations.AlterField(
            model_name='venta',
            name='pago',
            field=models.IntegerField(choices=[(1, 'Efectivo A Pagar'), (2, 'Efectivo Pagado'), (3, 'Enbalde Pago'), (4, 'Stripe Pago A Pagar'), (5, 'Stripe Pagado')], default=1),
        ),
    ]