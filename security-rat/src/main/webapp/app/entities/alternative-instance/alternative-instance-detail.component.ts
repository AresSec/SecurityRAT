import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlternativeInstance } from 'app/shared/model/alternative-instance.model';

@Component({
  selector: 'jhi-alternative-instance-detail',
  templateUrl: './alternative-instance-detail.component.html'
})
export class AlternativeInstanceDetailComponent implements OnInit {
  alternativeInstance: IAlternativeInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alternativeInstance }) => (this.alternativeInstance = alternativeInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
