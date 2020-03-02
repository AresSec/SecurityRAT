import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';
import { TrainingRequirementNodeService } from './training-requirement-node.service';
import { TrainingRequirementNodeDeleteDialogComponent } from './training-requirement-node-delete-dialog.component';

@Component({
  selector: 'jhi-training-requirement-node',
  templateUrl: './training-requirement-node.component.html'
})
export class TrainingRequirementNodeComponent implements OnInit, OnDestroy {
  trainingRequirementNodes?: ITrainingRequirementNode[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected trainingRequirementNodeService: TrainingRequirementNodeService,
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
      this.trainingRequirementNodeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITrainingRequirementNode[]>) => (this.trainingRequirementNodes = res.body || []));
      return;
    }

    this.trainingRequirementNodeService
      .query()
      .subscribe((res: HttpResponse<ITrainingRequirementNode[]>) => (this.trainingRequirementNodes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrainingRequirementNodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrainingRequirementNode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrainingRequirementNodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('trainingRequirementNodeListModification', () => this.loadAll());
  }

  delete(trainingRequirementNode: ITrainingRequirementNode): void {
    const modalRef = this.modalService.open(TrainingRequirementNodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trainingRequirementNode = trainingRequirementNode;
  }
}
