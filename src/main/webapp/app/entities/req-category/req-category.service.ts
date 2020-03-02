import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IReqCategory } from 'app/shared/model/req-category.model';

type EntityResponseType = HttpResponse<IReqCategory>;
type EntityArrayResponseType = HttpResponse<IReqCategory[]>;

@Injectable({ providedIn: 'root' })
export class ReqCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/req-categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/req-categories';

  constructor(protected http: HttpClient) {}

  create(reqCategory: IReqCategory): Observable<EntityResponseType> {
    return this.http.post<IReqCategory>(this.resourceUrl, reqCategory, { observe: 'response' });
  }

  update(reqCategory: IReqCategory): Observable<EntityResponseType> {
    return this.http.put<IReqCategory>(this.resourceUrl, reqCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReqCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReqCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReqCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
