import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { CollectionInstanceComponent } from './collection-instance.component';
import { CollectionInstanceDetailComponent } from './collection-instance-detail.component';
import { CollectionInstanceUpdateComponent } from './collection-instance-update.component';
import { CollectionInstanceDeleteDialogComponent } from './collection-instance-delete-dialog.component';
import { collectionInstanceRoute } from './collection-instance.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(collectionInstanceRoute)],
  declarations: [
    CollectionInstanceComponent,
    CollectionInstanceDetailComponent,
    CollectionInstanceUpdateComponent,
    CollectionInstanceDeleteDialogComponent
  ],
  entryComponents: [CollectionInstanceDeleteDialogComponent]
})
export class SecurityRatCollectionInstanceModule {}
