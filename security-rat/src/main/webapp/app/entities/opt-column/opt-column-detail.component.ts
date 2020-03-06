import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptColumn } from 'app/shared/model/opt-column.model';

@Component({
  selector: 'jhi-opt-column-detail',
  templateUrl: './opt-column-detail.component.html'
})
export class OptColumnDetailComponent implements OnInit {
  optColumn: IOptColumn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optColumn }) => (this.optColumn = optColumn));
  }

  previousState(): void {
    window.history.back();
  }
}
