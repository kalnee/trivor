import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Transcript e2e test', () => {

    let navBarPage: NavBarPage;
    let transcriptDialogPage: TranscriptDialogPage;
    let transcriptComponentsPage: TranscriptComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Transcripts', () => {
        navBarPage.goToEntity('transcript');
        transcriptComponentsPage = new TranscriptComponentsPage();
        expect(transcriptComponentsPage.getTitle()).toMatch(/gatewayApp.transcript.home.title/);

    });

    it('should load create Transcript dialog', () => {
        transcriptComponentsPage.clickOnCreateButton();
        transcriptDialogPage = new TranscriptDialogPage();
        expect(transcriptDialogPage.getModalTitle()).toMatch(/gatewayApp.transcript.home.createOrEditLabel/);
        transcriptDialogPage.close();
    });

    it('should create and save Transcripts', () => {
        transcriptComponentsPage.clickOnCreateButton();
        transcriptDialogPage.setNameInput('name');
        expect(transcriptDialogPage.getNameInput()).toMatch('name');
        transcriptDialogPage.typeSelectLastOption();
        transcriptDialogPage.setImdbIdInput('imdbId');
        expect(transcriptDialogPage.getImdbIdInput()).toMatch('imdbId');
        transcriptDialogPage.setCoverUrlInput('coverUrl');
        expect(transcriptDialogPage.getCoverUrlInput()).toMatch('coverUrl');
        transcriptDialogPage.setCreatedAtInput(12310020012301);
        expect(transcriptDialogPage.getCreatedAtInput()).toMatch('2001-12-31T02:30');
        transcriptDialogPage.setDescriptionInput('description');
        expect(transcriptDialogPage.getDescriptionInput()).toMatch('description');
        transcriptDialogPage.setYearInput('5');
        expect(transcriptDialogPage.getYearInput()).toMatch('5');
        transcriptDialogPage.setGenresInput('genres');
        expect(transcriptDialogPage.getGenresInput()).toMatch('genres');
        transcriptDialogPage.setCountryInput('country');
        expect(transcriptDialogPage.getCountryInput()).toMatch('country');
        transcriptDialogPage.setDurationInput('5');
        expect(transcriptDialogPage.getDurationInput()).toMatch('5');
        transcriptDialogPage.setLanguageInput('language');
        expect(transcriptDialogPage.getLanguageInput()).toMatch('language');
        transcriptDialogPage.save();
        expect(transcriptDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TranscriptComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-transcript div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TranscriptDialogPage {
    modalTitle = element(by.css('h4#myTranscriptLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    typeSelect = element(by.css('select#field_type'));
    imdbIdInput = element(by.css('input#field_imdbId'));
    coverUrlInput = element(by.css('input#field_coverUrl'));
    createdAtInput = element(by.css('input#field_createdAt'));
    descriptionInput = element(by.css('input#field_description'));
    yearInput = element(by.css('input#field_year'));
    genresInput = element(by.css('input#field_genres'));
    countryInput = element(by.css('input#field_country'));
    durationInput = element(by.css('input#field_duration'));
    languageInput = element(by.css('input#field_language'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setTypeSelect = function (type) {
        this.typeSelect.sendKeys(type);
    }

    getTypeSelect = function () {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    typeSelectLastOption = function () {
        this.typeSelect.all(by.tagName('option')).last().click();
    }
    setImdbIdInput = function (imdbId) {
        this.imdbIdInput.sendKeys(imdbId);
    }

    getImdbIdInput = function () {
        return this.imdbIdInput.getAttribute('value');
    }

    setCoverUrlInput = function (coverUrl) {
        this.coverUrlInput.sendKeys(coverUrl);
    }

    getCoverUrlInput = function () {
        return this.coverUrlInput.getAttribute('value');
    }

    setCreatedAtInput = function (createdAt) {
        this.createdAtInput.sendKeys(createdAt);
    }

    getCreatedAtInput = function () {
        return this.createdAtInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setYearInput = function (year) {
        this.yearInput.sendKeys(year);
    }

    getYearInput = function () {
        return this.yearInput.getAttribute('value');
    }

    setGenresInput = function (genres) {
        this.genresInput.sendKeys(genres);
    }

    getGenresInput = function () {
        return this.genresInput.getAttribute('value');
    }

    setCountryInput = function (country) {
        this.countryInput.sendKeys(country);
    }

    getCountryInput = function () {
        return this.countryInput.getAttribute('value');
    }

    setDurationInput = function (duration) {
        this.durationInput.sendKeys(duration);
    }

    getDurationInput = function () {
        return this.durationInput.getAttribute('value');
    }

    setLanguageInput = function (language) {
        this.languageInput.sendKeys(language);
    }

    getLanguageInput = function () {
        return this.languageInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
