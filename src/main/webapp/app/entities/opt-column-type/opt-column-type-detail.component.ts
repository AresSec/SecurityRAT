import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptColumnType } from 'app/shared/model/opt-column-type.model';

@Component({
  selector: 'jhi-opt-column-type-detail',
  templateUrl: './opt-column-type-detail.component.html'
})
export class OptColumnTypeDetailComponent implements OnInit {
  optColumnType: IOptColumnType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optColumnType }) => (this.optColumnType = optColumnType));
  }

  previousState(): void {
    window.history.back();
  }
}
