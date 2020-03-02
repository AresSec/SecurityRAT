import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrainingTreeNode, TrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from './training-tree-node.service';
import { TrainingTreeNodeComponent } from './training-tree-node.component';
import { TrainingTreeNodeDetailComponent } from './training-tree-node-detail.component';
import { TrainingTreeNodeUpdateComponent } from './training-tree-node-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingTreeNodeResolve implements Resolve<ITrainingTreeNode> {
  constructor(private service: TrainingTreeNodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingTreeNode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trainingTreeNode: HttpResponse<TrainingTreeNode>) => {
          if (trainingTreeNode.body) {
            return of(trainingTreeNode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingTreeNode());
  }
}

export const trainingTreeNodeRoute: Routes = [
  {
    path: '',
    component: TrainingTreeNodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingTreeNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrainingTreeNodeDetailComponent,
    resolve: {
      trainingTreeNode: TrainingTreeNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingTreeNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrainingTreeNodeUpdateComponent,
    resolve: {
      trainingTreeNode: TrainingTreeNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingTreeNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrainingTreeNodeUpdateComponent,
    resolve: {
      trainingTreeNode: TrainingTreeNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingTreeNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
