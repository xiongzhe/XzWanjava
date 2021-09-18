package com.xiongz.wanjava.common.icon;

import com.joanzapata.iconify.Icon;

/**
 * 营销e网通图标库
 * Created by xiongz on 2017/12/10
 */
public enum SfaIcons implements Icon {

    ic_close('\ue7f5'),
    ic_menu_home('\ue600'),
    ic_menu_msg('\ue64d'),
    ic_menu_contacts('\ue65e'),
    ic_menu_form('\ue67f'),
    ic_menu_mine('\ue616'),
    bg_circle('\ue601'),
    ic_my_visit('\ue771'),
    ic_my_reimburse('\ue659'),
    ic_my_attendance('\ue619'),
    ic_banner_click('\ue602'),
    ic_setting('\ue614'),
    ic_pwd('\ue634'),
    ic_org('\ue695'),
    ic_client('\ue675'),
    ic_more('\ue60e'),
    ic_sign_in('\ue637'),
    ic_visit_client('\ue609'),
    ic_activity_imgs('\ue62f'),
    ic_invoice_title('\ue635'),
    ic_product_complaint('\ue6cf'),
    ic_visit_search('\ue699'),
    ic_product_search('\ue62c'),
    ic_scan('\ue630'),
    ic_client_location('\ue606'),
    ic_policy('\ue65d'),
    ic_trumpet('\ue604'),
    ic_pay('\ue627'),
    ic_bonus('\ue672'),
    ic_home_client('\ue66c'),
    ic_staff('\ue68e'),
    ic_map('\ue605'),
    ic_clear('\ue61c'),
    ic_work_report('\ue825'),
    ic_business_travel('\ue603'),
    ic_work_way('\ue62b'),
    ic_train('\ue6cc'),
    ic_minus('\ue67d'),
    ic_plus('\ue67e'),
    ic_btn_phone('\ue67c'),
    ic_navigation('\ue6a0'),
    ic_camera_upload('\ue607'),
    ic_camera_confirm('\ue631'),
    ic_camera_close('\ue608'),
    ic_camera_direction('\ue639'),
    ic_light_off('\ue876'),
    ic_light_on('\ue873'),
    ic_camera_yes('\ue60a'),
    ic_camera_no('\ue649'),
    ic_images_remove('\ue664'),
    ic_popup_up('\ue60b'),
    ic_popup_down('\ue60c'),
    ic_position('\ue64b'),
    ic_pwd_close('\ue64e'),
    ic_pwd_open('\ue62d'),
    ic_img_error('\ue611'),
    ic_bus_license('\ue617'),
    ic_location('\ue63f'),
    ic_warn('\ue6a2'),
    ic_visit_no_complete('\ue706'),
    ic_back('\ue6e1'),
    ic_edit('\ue636'),
    ic_yes('\ue61a'),
    ic_img_more('\ue63c'),
    ic_location_update('\ue612'),
    ic_time('\ue6ed'),
    ic_record('\ue65f'),
    ic_complete('\ue62a'),
    ic_radar('\ue625'),
    ic_call('\ue7f9'),
    ic_home_more('\ue6ee'),
    ic_notice('\ue647'),
    ic_new('\ue61d'),
    ic_send_msg('\ue628'),
    ic_show_time('\ue74f'),
    ic_show_eye('\ue661'),
    ic_answer('\ue63e'),
    ic_question('\ue63d'),
    ic_date('\ue62e'),
    ic_salary('\ue783'),
    ic_bar_code('\ue622'),
    ic_evection('\ue652'),
    ic_daily_reimbursement('\ue680'),
    ic_year_bonus('\ue641'),
    ic_salary_score('\ue6dc'),
    ic_news('\ue646'),
    ic_action_guide('\ue621'),
    ic_cloud_word('\ue610'),
    ic_inside_buy_register('\ue640'),
    ic_question_survey('\ue7d8'),
    ic_out_apply('\ue645'),
    ic_late_cancel('\ue613'),
    ic_mobile_attendance('\ue6df'),
    ic_out_attendance('\ue626'),
    ic_out_attendance_record('\ue620'),
    ic_attendance_approval('\ue61f'),
    ic_expenses_approval('\ue643'),
    ic_id_card('\ue674'),
    ic_leader('\ue868'),
    ic_list('\ue6d8'),
    ic_battery('\ue623'),
    ic_email('\ue685'),
    ic_down('\ue644'),
    ic_archives('\ue701'),
    ic_confer('\ue64a'),
    ic_confer_summary('\ue670'),
    ic_hotel('\ue64f'),
    ic_cus_complaint('\ue653'),
    ic_adr('\ue6c3'),
    ic_market_check('\ue660'),
    ic_factory_more('\ue651'),
    ic_visit_summary('\ue650'),
    ic_run('\ue654'),
    ic_tup('\ue671'),
    ic_work_proof('\ue678'),
    ic_work_card('\ue663'),
    ic_checkbox_yes('\ue667'),
    ic_checkbox_no('\ue666');

    private char character;

    SfaIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}