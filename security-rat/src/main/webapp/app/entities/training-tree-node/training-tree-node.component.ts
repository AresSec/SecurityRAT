import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from './training-tree-node.service';
import { TrainingTreeNodeDeleteDialogComponent } from './training-tree-node-delete-dialog.component';

@Component({
  selector: 'jhi-training-tree-node',
  templateUrl: './training-tree-node.component.html'
})
export class TrainingTreeNodeComponent implements OnInit, OnDestroy {
  trainingTreeNodes?: ITrainingTreeNode[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected trainingTreeNodeService: TrainingTreeNodeService,
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
      this.trainingTreeNodeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingTreeNodes = res.body || []));
      return;
    }

    this.trainingTreeNodeService.query().subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingTreeNodes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrainingTreeNodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrainingTreeNode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrainingTreeNodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('trainingTreeNodeListModification', () => this.loadAll());
  }

  delete(trainingTreeNode: ITrainingTreeNode): void {
    const modalRef = this.modalService.open(TrainingTreeNodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trainingTreeNode = trainingTreeNode;
  }
}
