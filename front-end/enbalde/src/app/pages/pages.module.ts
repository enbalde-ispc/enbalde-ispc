import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ContactoComponent } from './contacto/contacto.component';
import { RegistracionComponent } from './registracion/registracion.component';
import { AltaProductoComponent } from './alta-producto/alta-producto.component';

@NgModule({
  declarations: [
    HomeComponent,
    ContactoComponent,
    RegistracionComponent,
    AltaProductoComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class PagesModule { }
