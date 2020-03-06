import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptColumnContent } from 'app/shared/model/opt-column-content.model';

@Component({
  selector: 'jhi-opt-column-content-detail',
  templateUrl: './opt-column-content-detail.component.html'
})
export class OptColumnContentDetailComponent implements OnInit {
  optColumnContent: IOptColumnContent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optColumnContent }) => (this.optColumnContent = optColumnContent));
  }

  previousState(): void {
    window.history.back();
  }
}
