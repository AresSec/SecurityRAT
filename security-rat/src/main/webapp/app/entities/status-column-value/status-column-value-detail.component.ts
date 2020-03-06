import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusColumnValue } from 'app/shared/model/status-column-value.model';

@Component({
  selector: 'jhi-status-column-value-detail',
  templateUrl: './status-column-value-detail.component.html'
})
export class StatusColumnValueDetailComponent implements OnInit {
  statusColumnValue: IStatusColumnValue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusColumnValue }) => (this.statusColumnValue = statusColumnValue));
  }

  previousState(): void {
    window.history.back();
  }
}
