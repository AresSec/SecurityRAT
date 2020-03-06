import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';
import { TrainingGeneratedSlideNodeService } from './training-generated-slide-node.service';
import { TrainingGeneratedSlideNodeDeleteDialogComponent } from './training-generated-slide-node-delete-dialog.component';

@Component({
  selector: 'jhi-training-generated-slide-node',
  templateUrl: './training-generated-slide-node.component.html'
})
export class TrainingGeneratedSlideNodeComponent implements OnInit, OnDestroy {
  trainingGeneratedSlideNodes?: ITrainingGeneratedSlideNode[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected trainingGeneratedSlideNodeService: TrainingGeneratedSlideNodeService,
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
      this.trainingGeneratedSlideNodeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITrainingGeneratedSlideNode[]>) => (this.trainingGeneratedSlideNodes = res.body || []));
      return;
    }

    this.trainingGeneratedSlideNodeService
      .query()
      .subscribe((res: HttpResponse<ITrainingGeneratedSlideNode[]>) => (this.trainingGeneratedSlideNodes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrainingGeneratedSlideNodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrainingGeneratedSlideNode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrainingGeneratedSlideNodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('trainingGeneratedSlideNodeListModification', () => this.loadAll());
  }

  delete(trainingGeneratedSlideNode: ITrainingGeneratedSlideNode): void {
    const modalRef = this.modalService.open(TrainingGeneratedSlideNodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trainingGeneratedSlideNode = trainingGeneratedSlideNode;
  }
}
