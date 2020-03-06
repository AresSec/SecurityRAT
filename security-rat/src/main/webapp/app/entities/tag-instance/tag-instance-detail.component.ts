import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITagInstance } from 'app/shared/model/tag-instance.model';

@Component({
  selector: 'jhi-tag-instance-detail',
  templateUrl: './tag-instance-detail.component.html'
})
export class TagInstanceDetailComponent implements OnInit {
  tagInstance: ITagInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tagInstance }) => (this.tagInstance = tagInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
