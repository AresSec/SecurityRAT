import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingBranchNode } from 'app/shared/model/training-branch-node.model';
import { TrainingBranchNodeService } from './training-branch-node.service';
import { TrainingBranchNodeDeleteDialogComponent } from './training-branch-node-delete-dialog.component';

@Component({
  selector: 'jhi-training-branch-node',
  templateUrl: './training-branch-node.component.html'
})
export class TrainingBranchNodeComponent implements OnInit, OnDestroy {
  trainingBranchNodes?: ITrainingBranchNode[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected trainingBranchNodeService: TrainingBranchNodeService,
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
      this.trainingBranchNodeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITrainingBranchNode[]>) => (this.trainingBranchNodes = res.body || []));
      return;
    }

    this.trainingBranchNodeService
      .query()
      .subscribe((res: HttpResponse<ITrainingBranchNode[]>) => (this.trainingBranchNodes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrainingBranchNodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrainingBranchNode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrainingBranchNodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('trainingBranchNodeListModification', () => this.loadAll());
  }

  delete(trainingBranchNode: ITrainingBranchNode): void {
    const modalRef = this.modalService.open(TrainingBranchNodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trainingBranchNode = trainingBranchNode;
  }
}
