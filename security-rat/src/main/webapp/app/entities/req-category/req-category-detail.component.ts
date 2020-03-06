import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReqCategory } from 'app/shared/model/req-category.model';

@Component({
  selector: 'jhi-req-category-detail',
  templateUrl: './req-category-detail.component.html'
})
export class ReqCategoryDetailComponent implements OnInit {
  reqCategory: IReqCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reqCategory }) => (this.reqCategory = reqCategory));
  }

  previousState(): void {
    window.history.back();
  }
}
