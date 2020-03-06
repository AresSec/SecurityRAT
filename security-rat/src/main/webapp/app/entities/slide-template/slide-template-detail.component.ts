import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISlideTemplate } from 'app/shared/model/slide-template.model';

@Component({
  selector: 'jhi-slide-template-detail',
  templateUrl: './slide-template-detail.component.html'
})
export class SlideTemplateDetailComponent implements OnInit {
  slideTemplate: ISlideTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ slideTemplate }) => (this.slideTemplate = slideTemplate));
  }

  previousState(): void {
    window.history.back();
  }
}
