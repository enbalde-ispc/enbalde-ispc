# Generated by Django 4.2.1 on 2023-05-17 19:13

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Envio',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('nombre', models.CharField(max_length=40)),
                ('monto', models.DecimalField(decimal_places=2, max_digits=10, max_length=10)),
            ],
            options={
                'verbose_name': 'Tipos de envios disponibles',
                'verbose_name_plural': 'Envios',
                'db_table': 'Envio',
            },
        ),
        migrations.CreateModel(
            name='TipoArticulo',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('nombre', models.CharField(max_length=40)),
            ],
        ),
        migrations.CreateModel(
            name='Producto',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('nombre', models.CharField(max_length=200)),
                ('descripcion', models.CharField(max_length=200)),
                ('precio', models.DecimalField(decimal_places=2, max_digits=10, max_length=10)),
                ('costo', models.DecimalField(decimal_places=2, max_digits=10, max_length=10)),
                ('alicuota', models.DecimalField(decimal_places=2, max_digits=10, max_length=10)),
                ('imagen', models.CharField(max_length=512)),
                ('cantidad', models.IntegerField(default=0)),
                ('id_tipo', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='enbalde.tipoarticulo')),
            ],
            options={
                'verbose_name': 'Productos del negocio',
                'verbose_name_plural': 'Productos',
                'db_table': 'Producto',
            },
        ),
    ]
