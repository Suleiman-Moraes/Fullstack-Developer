import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-input-padrao',
  templateUrl: './input-padrao.component.html',
  styleUrls: ['./input-padrao.component.css']
})
export class InputPadraoComponent implements OnInit {

  @Input('for-name') forName: string;
  @Input('nome') nome: string;
  @Input('resource-form') resourceForm: FormGroup;

  constructor() { }

  ngOnInit() {
  }

}
