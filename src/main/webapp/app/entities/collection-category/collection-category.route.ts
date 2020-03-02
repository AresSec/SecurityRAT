import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICollectionCategory, CollectionCategory } from 'app/shared/model/collection-category.model';
import { CollectionCategoryService } from './collection-category.service';
import { CollectionCategoryComponent } from './collection-category.component';
import { CollectionCategoryDetailComponent } from './collection-category-detail.component';
import { CollectionCategoryUpdateComponent } from './collection-category-update.component';

@Injectable({ providedIn: 'root' })
export class CollectionCategoryResolve implements Resolve<ICollectionCategory> {
  constructor(private service: CollectionCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollectionCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collectionCategory: HttpResponse<CollectionCategory>) => {
          if (collectionCategory.body) {
            return of(collectionCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CollectionCategory());
  }
}

export const collectionCategoryRoute: Routes = [
  {
    path: '',
    component: CollectionCategoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CollectionCategoryDetailComponent,
    resolve: {
      collectionCategory: CollectionCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CollectionCategoryUpdateComponent,
    resolve: {
      collectionCategory: CollectionCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CollectionCategoryUpdateComponent,
    resolve: {
      collectionCategory: CollectionCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.collectionCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
