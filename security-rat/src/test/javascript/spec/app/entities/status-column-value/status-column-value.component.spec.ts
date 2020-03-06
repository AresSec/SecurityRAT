import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { StatusColumnValueComponent } from 'app/entities/status-column-value/status-column-value.component';
import { StatusColumnValueService } from 'app/entities/status-column-value/status-column-value.service';
import { StatusColumnValue } from 'app/shared/model/status-column-value.model';

describe('Component Tests', () => {
  describe('StatusColumnValue Management Component', () => {
    let comp: StatusColumnValueComponent;
    let fixture: ComponentFixture<StatusColumnValueComponent>;
    let service: StatusColumnValueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [StatusColumnValueComponent]
      })
        .overrideTemplate(StatusColumnValueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatusColumnValueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatusColumnValueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StatusColumnValue(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.statusColumnValues && comp.statusColumnValues[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
