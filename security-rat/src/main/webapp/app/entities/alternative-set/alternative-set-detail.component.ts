import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlternativeSet } from 'app/shared/model/alternative-set.model';

@Component({
  selector: 'jhi-alternative-set-detail',
  templateUrl: './alternative-set-detail.component.html'
})
export class AlternativeSetDetailComponent implements OnInit {
  alternativeSet: IAlternativeSet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alternativeSet }) => (this.alternativeSet = alternativeSet));
  }

  previousState(): void {
    window.history.back();
  }
}
