import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingCategoryNode } from 'app/shared/model/training-category-node.model';
import { TrainingCategoryNodeService } from './training-category-node.service';
import { TrainingCategoryNodeDeleteDialogComponent } from './training-category-node-delete-dialog.component';

@Component({
  selector: 'jhi-training-category-node',
  templateUrl: './training-category-node.component.html'
})
export class TrainingCategoryNodeComponent implements OnInit, OnDestroy {
  trainingCategoryNodes?: ITrainingCategoryNode[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected trainingCategoryNodeService: TrainingCategoryNodeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.trainingCategoryNodeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITrainingCategoryNode[]>) => (this.trainingCategoryNodes = res.body || []));
      return;
    }

    this.trainingCategoryNodeService
      .query()
      .subscribe((res: HttpResponse<ITrainingCategoryNode[]>) => (this.trainingCategoryNodes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrainingCategoryNodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrainingCategoryNode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrainingCategoryNodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('trainingCategoryNodeListModification', () => this.loadAll());
  }

  delete(trainingCategoryNode: ITrainingCategoryNode): void {
    const modalRef = this.modalService.open(TrainingCategoryNodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trainingCategoryNode = trainingCategoryNode;
  }
}
