import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { CollectionCategoryComponent } from './collection-category.component';
import { CollectionCategoryDetailComponent } from './collection-category-detail.component';
import { CollectionCategoryUpdateComponent } from './collection-category-update.component';
import { CollectionCategoryDeleteDialogComponent } from './collection-category-delete-dialog.component';
import { collectionCategoryRoute } from './collection-category.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(collectionCategoryRoute)],
  declarations: [
    CollectionCategoryComponent,
    CollectionCategoryDetailComponent,
    CollectionCategoryUpdateComponent,
    CollectionCategoryDeleteDialogComponent
  ],
  entryComponents: [CollectionCategoryDeleteDialogComponent]
})
export class SecurityRatCollectionCategoryModule {}
