import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITagCategory } from 'app/shared/model/tag-category.model';

@Component({
  selector: 'jhi-tag-category-detail',
  templateUrl: './tag-category-detail.component.html'
})
export class TagCategoryDetailComponent implements OnInit {
  tagCategory: ITagCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tagCategory }) => (this.tagCategory = tagCategory));
  }

  previousState(): void {
    window.history.back();
  }
}
