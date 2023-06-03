import { Component, Input } from '@angular/core';
import { Venta } from '../../models/modelo.venta';
import { VentasService } from 'src/app/services/ventas.service';

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.css'],
  providers: [ VentasService ]
})

export class VentasComponent {
  @Input() ventas: Venta [] = [];

  constructor(public ventasService: VentasService) {
  }

  ngOnInit() : void {
    this.ventasService.obtenerVentas()
      .subscribe((ventas: Venta[]) => {
        this.ventas = ventas;
      });
  }
}
