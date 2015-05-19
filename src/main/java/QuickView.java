// Copyright 2015 Indeewari Akarawita
//
//    This file is a part of ANDROPHSY
//
//    ANDROPHSY is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.
																																																																																																																																																																																																						import java.io.BufferedReader;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Canvas;


/**
 * @author indeewari
 *
 */
public class QuickView extends Composite {
	ArrayList<String> xval = new ArrayList<String>();
	ArrayList<String> yval = new ArrayList<String>();	
	
	private Text txtCcnSrch;
	private Text txtCcnOffset;
	
	private Table tblCcnStat;
	private Canvas canvasCcn;
	private double max;
	private int index;
	private Text text;
	private Text txtTelephoneSrch;
	private Canvas canvasTele;
	private Table tblTeleStat;
	private static FeatureStats urlAllStat;
	private static FeatureStats domainStat;
	private static FeatureOffset ccnoffset;
	private static FeatureStats ccnStat;
	private static FeatureOffset teleoffset;
	private static FeatureStats TeleStat;
	private static FeatureOffset emailoffset;
	private static FeatureStats emailStat;
	private static FeatureOffset domainoffset;
	private static FeatureStats emailDomainStat;
	private static FeatureOffset urloffset;
	private static FeatureStats urlsearchStat;
	private static FeatureStats fbidstat;
	private static FeatureStats fbaddressstat;
	private static FeatureOffset macoffset;
	private static FeatureStats macstat;
	private static FeatureOffset ipoffset;
	private static FeatureStats ipstat;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public QuickView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		TableColumn tblclmnIndex;
		TableColumn tblclmnValue;
		TableColumn tblclmnFrequency;
		
		TabFolder tabFeatures = new TabFolder(this, SWT.NONE);
		tabFeatures.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmCreditCard = new TabItem(tabFeatures, SWT.NONE);
		tbtmCreditCard.setText("Credit Card Numbers");
		
		Composite cmpccn = new Composite(tabFeatures, SWT.NONE);
		tbtmCreditCard.setControl(cmpccn);
		cmpccn.setLayout(new GridLayout(1, false));
		
		TabFolder tabCcn = new TabFolder(cmpccn, SWT.NONE);
		tabCcn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtCcnDetail = new TabItem(tabCcn, SWT.NONE);
		tbtCcnDetail.setText("Detail");
		ccnoffset = new FeatureOffset(tabCcn, SWT.NONE);
		tbtCcnDetail.setControl(ccnoffset);
		
		TabItem tbtCcnSummary = new TabItem(tabCcn, SWT.NONE);
		tbtCcnSummary.setText("Summary");
		
		ccnStat = new FeatureStats(tabCcn, SWT.NONE);
		tbtCcnSummary.setControl(ccnStat);
		
		TabItem tbtmTelephone = new TabItem(tabFeatures, SWT.NONE);
		tbtmTelephone.setText("Telephone Numbers");
		
		Composite cmptelephone = new Composite(tabFeatures, SWT.NONE);
		tbtmTelephone.setControl(cmptelephone);
		cmptelephone.setLayout(new GridLayout(1, false));
		
		TabFolder tabTelephone = new TabFolder(cmptelephone, SWT.NONE);
		tabTelephone.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtTelephoneDetail = new TabItem(tabTelephone, SWT.NONE);
		tbtTelephoneDetail.setText("Detail");
		teleoffset = new FeatureOffset(tabTelephone, SWT.NONE);
		tbtTelephoneDetail.setControl(teleoffset);
		
		TabItem tbtTeleSummary = new TabItem(tabTelephone, SWT.NONE);
		tbtTeleSummary.setText("Summary");
		
		TeleStat = new FeatureStats(tabTelephone, SWT.NONE);
		tbtTeleSummary.setControl(TeleStat);
		//E-Mail
		TabItem tbtmEmail = new TabItem(tabFeatures, SWT.NONE);
		tbtmEmail.setText("Email Address");
		Composite cmpemail = new Composite(tabFeatures, SWT.NONE);
		tbtmEmail.setControl(cmpemail);
		cmpemail.setLayout(new GridLayout(1, false));
		
		TabFolder tabEmail = new TabFolder(cmpemail, SWT.NONE);
		tabEmail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtEmailDetail = new TabItem(tabEmail, SWT.NONE);
		tbtEmailDetail.setText("Detail");
		
		emailoffset = new FeatureOffset(tabEmail, SWT.NONE);
		tbtEmailDetail.setControl(emailoffset);
		
		TabItem tbtEmailSummary = new TabItem(tabEmail, SWT.NONE);
		tbtEmailSummary.setText("Summary");
		
		emailStat = new FeatureStats(tabEmail, SWT.NONE);
		tbtEmailSummary.setControl(emailStat);
		
		//
		TabItem tbtmDomainNames = new TabItem(tabFeatures, SWT.NONE);
		tbtmDomainNames.setText("Domain Names");
		
		Composite cmpdomain = new Composite(tabFeatures, SWT.NONE);
		tbtmDomainNames.setControl(cmpdomain);
		cmpdomain.setLayout(new GridLayout(1, false));
		
		TabFolder tabDomain = new TabFolder(cmpdomain, SWT.NONE);
		tabDomain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtDomainSummary = new TabItem(tabDomain, SWT.NONE);
		tbtDomainSummary.setText("All Domains");
				
		domainStat = new FeatureStats(tabDomain, SWT.NONE);
		tbtDomainSummary.setControl(domainStat);
		
		TabItem tbtmEmailDomain = new TabItem(tabDomain, SWT.NONE);
		tbtmEmailDomain.setText("Email Domains");
		
		emailDomainStat = new FeatureStats(tabDomain, SWT.NONE);
		tbtmEmailDomain.setControl(emailDomainStat);
		
		//
		TabItem tbtmUrl = new TabItem(tabFeatures, SWT.NONE);
		tbtmUrl.setText("URL");
		
		Composite cmpurl = new Composite(tabFeatures, SWT.NONE);
		tbtmUrl.setControl(cmpurl);
		cmpurl.setLayout(new GridLayout(1, false));
		
		TabFolder tabUrl = new TabFolder(cmpurl, SWT.NONE);
		tabUrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			
		TabItem tbtUrlSummary = new TabItem(tabUrl, SWT.NONE);
		tbtUrlSummary.setText("URL Pattern");
		
		urlAllStat = new FeatureStats(tabUrl, SWT.None);
		tbtUrlSummary.setControl(urlAllStat);
		
		TabItem tbtUrlSearch = new TabItem(tabUrl, SWT.NONE);
		tbtUrlSearch.setText("Search Pattern");
		
		urlsearchStat = new FeatureStats(tabUrl, SWT.NONE);
		tbtUrlSearch.setControl(urlsearchStat);
		
		TabItem tbtmFacebook = new TabItem(tabUrl, SWT.NONE);
		tbtmFacebook.setText("Facebook ID");

		fbidstat = new FeatureStats(tabUrl, SWT.NONE);
		tbtmFacebook.setControl(fbidstat);
		
		TabItem tbtmFacebookAdd = new TabItem(tabUrl, SWT.NONE);
		tbtmFacebookAdd.setText("Facebook Address");
		
		fbaddressstat = new FeatureStats(tabUrl, SWT.NONE);
		tbtmFacebookAdd.setControl(fbaddressstat);
		
		TabItem tbtmMacAddress = new TabItem(tabFeatures, SWT.NONE);
		tbtmMacAddress.setText("MAC Address");
		
		Composite cmpmacaddr = new Composite(tabFeatures, SWT.NONE);
		tbtmMacAddress.setControl(cmpmacaddr);
		cmpmacaddr.setLayout(new GridLayout(1, false));
		
		TabFolder tabMac = new TabFolder(cmpmacaddr, SWT.NONE);
		tabMac.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		TabItem tbtMacDetail = new TabItem(tabMac, SWT.NONE);
		tbtMacDetail.setText("Detail");
		
		macoffset = new FeatureOffset(tabMac, SWT.NONE);
		tbtMacDetail.setControl(macoffset);
		
		TabItem tbtMacSummary = new TabItem(tabMac, SWT.NONE);
		tbtMacSummary.setText("Summary");
		
		macstat = new FeatureStats(tabMac, SWT.NONE);
		tbtMacSummary.setControl(macstat);
		
		TabItem tbtmIpAddress = new TabItem(tabFeatures, SWT.NONE);
		tbtmIpAddress.setText("IP Address");
		
		Composite cmpipaddr = new Composite(tabFeatures, SWT.NONE);
		tbtmIpAddress.setControl(cmpipaddr);
		cmpipaddr.setLayout(new GridLayout(1, false));
		
		TabFolder tabIp = new TabFolder(cmpipaddr, SWT.NONE);
		tabIp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		TabItem tbtIpDetail = new TabItem(tabIp, SWT.NONE);
		tbtIpDetail.setText("Detail");
		
		ipoffset = new FeatureOffset(tabIp, SWT.NONE);
		tbtIpDetail.setControl(ipoffset);
		
		TabItem tbtIpSummary = new TabItem(tabIp, SWT.NONE);
		tbtIpSummary.setText("Summary");
		
		ipstat = new FeatureStats(tabIp, SWT.NONE);
		tbtIpSummary.setControl(ipstat);
		
	}
	
	public static void populateData() {

		ccnoffset.showDataExtracts(AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/ccn.txt");
		ccnStat.generateStatistics("Credit Card", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/ccn_histogram.txt");
		
		teleoffset.showDataExtracts(AndrospyMain.gb_CasePath +"Analysis/Quick_Extract/telephone.txt");
		TeleStat.generateStatistics("Telephone Numbers", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/telephone_histogram.txt");
		
		emailoffset.showDataExtracts(AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/email.txt");
		emailStat.generateStatistics("Email Adress", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/email_histogram.txt");
		
		domainStat.generateStatistics("Domain Summary" , AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/domain_histogram.txt");
		emailDomainStat.generateStatistics("Email Domain", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/email_domain_histogram.txt");
		
		urlAllStat.generateStatistics("URL Summary", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/url_histogram.txt");
		fbidstat.generateStatistics("Facebook Profile ID", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/url_facebook-id.txt");
		fbaddressstat.generateStatistics("Facebook Address", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/url_facebook-address.txt");
		urlsearchStat.generateStatistics("Searched URL", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/url_searches.txt");
		
		macoffset.showDataExtracts(AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/ether.txt");
		macstat.generateStatistics("MAC Address", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/ether_histogram.txt");
		
		ipoffset.showDataExtracts(AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/ip.txt");
		ipstat.generateStatistics("MAC Address", AndrospyMain.gb_CasePath + "Analysis/Quick_Extract/ip_histogram.txt");
		
	}
	
}
