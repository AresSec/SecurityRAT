import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOptColumnContent, OptColumnContent } from 'app/shared/model/opt-column-content.model';
import { OptColumnContentService } from './opt-column-content.service';
import { IOptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from 'app/entities/opt-column/opt-column.service';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';
import { RequirementSkeletonService } from 'app/entities/requirement-skeleton/requirement-skeleton.service';

type SelectableEntity = IOptColumn | IRequirementSkeleton;

@Component({
  selector: 'jhi-opt-column-content-update',
  templateUrl: './opt-column-content-update.component.html'
})
export class OptColumnContentUpdateComponent implements OnInit {
  isSaving = false;
  optcolumns: IOptColumn[] = [];
  requirementskeletons: IRequirementSkeleton[] = [];

  editForm = this.fb.group({
    id: [],
    content: [],
    optColumn: [],
    requirementSkeleton: []
  });

  constructor(
    protected optColumnContentService: OptColumnContentService,
    protected optColumnService: OptColumnService,
    protected requirementSkeletonService: RequirementSkeletonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optColumnContent }) => {
      this.updateForm(optColumnContent);

      this.optColumnService.query().subscribe((res: HttpResponse<IOptColumn[]>) => (this.optcolumns = res.body || []));

      this.requirementSkeletonService
        .query()
        .subscribe((res: HttpResponse<IRequirementSkeleton[]>) => (this.requirementskeletons = res.body || []));
    });
  }

  updateForm(optColumnContent: IOptColumnContent): void {
    this.editForm.patchValue({
      id: optColumnContent.id,
      content: optColumnContent.content,
      optColumn: optColumnContent.optColumn,
      requirementSkeleton: optColumnContent.requirementSkeleton
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const optColumnContent = this.createFromForm();
    if (optColumnContent.id !== undefined) {
      this.subscribeToSaveResponse(this.optColumnContentService.update(optColumnContent));
    } else {
      this.subscribeToSaveResponse(this.optColumnContentService.create(optColumnContent));
    }
  }

  private createFromForm(): IOptColumnContent {
    return {
      ...new OptColumnContent(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      optColumn: this.editForm.get(['optColumn'])!.value,
      requirementSkeleton: this.editForm.get(['requirementSkeleton'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptColumnContent>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
