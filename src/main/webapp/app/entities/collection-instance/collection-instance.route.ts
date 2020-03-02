import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICollectionInstance, CollectionInstance } from 'app/shared/model/collection-instance.model';
import { CollectionInstanceService } from './collection-instance.service';
import { CollectionInstanceComponent } from './collection-instance.component';
import { CollectionInstanceDetailComponent } from './collection-instance-detail.component';
import { CollectionInstanceUpdateComponent } from './collection-instance-update.component';

@Injectable({ providedIn: 'root' })
export class CollectionInstanceResolve implements Resolve<ICollectionInstance> {
  constructor(private service: CollectionInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollectionInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collectionInstance: HttpResponse<CollectionInstance>) => {
          if (collectionInstance.body) {
            return of(collectionInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CollectionInstance());
  }
}

export const collectionInstanceRoute: Routes = [
  {
    path: '',
    component: CollectionInstanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CollectionInstanceDetailComponent,
    resolve: {
      collectionInstance: CollectionInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CollectionInstanceUpdateComponent,
    resolve: {
      collectionInstance: CollectionInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CollectionInstanceUpdateComponent,
    resolve: {
      collectionInstance: CollectionInstanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
