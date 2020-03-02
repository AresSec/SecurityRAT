import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusColumn } from 'app/shared/model/status-column.model';

@Component({
  selector: 'jhi-status-column-detail',
  templateUrl: './status-column-detail.component.html'
})
export class StatusColumnDetailComponent implements OnInit {
  statusColumn: IStatusColumn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusColumn }) => (this.statusColumn = statusColumn));
  }

  previousState(): void {
    window.history.back();
  }
}
