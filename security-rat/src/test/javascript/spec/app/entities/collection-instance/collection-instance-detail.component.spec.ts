import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { CollectionInstanceDetailComponent } from 'app/entities/collection-instance/collection-instance-detail.component';
import { CollectionInstance } from 'app/shared/model/collection-instance.model';

describe('Component Tests', () => {
  describe('CollectionInstance Management Detail Component', () => {
    let comp: CollectionInstanceDetailComponent;
    let fixture: ComponentFixture<CollectionInstanceDetailComponent>;
    const route = ({ data: of({ collectionInstance: new CollectionInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [CollectionInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CollectionInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollectionInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load collectionInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collectionInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
