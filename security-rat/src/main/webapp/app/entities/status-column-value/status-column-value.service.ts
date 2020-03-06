import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IStatusColumnValue } from 'app/shared/model/status-column-value.model';

type EntityResponseType = HttpResponse<IStatusColumnValue>;
type EntityArrayResponseType = HttpResponse<IStatusColumnValue[]>;

@Injectable({ providedIn: 'root' })
export class StatusColumnValueService {
  public resourceUrl = SERVER_API_URL + 'api/status-column-values';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/status-column-values';

  constructor(protected http: HttpClient) {}

  create(statusColumnValue: IStatusColumnValue): Observable<EntityResponseType> {
    return this.http.post<IStatusColumnValue>(this.resourceUrl, statusColumnValue, { observe: 'response' });
  }

  update(statusColumnValue: IStatusColumnValue): Observable<EntityResponseType> {
    return this.http.put<IStatusColumnValue>(this.resourceUrl, statusColumnValue, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusColumnValue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusColumnValue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusColumnValue[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
